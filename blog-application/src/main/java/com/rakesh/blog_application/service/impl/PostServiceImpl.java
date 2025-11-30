package com.rakesh.blog_application.service.impl;

import com.rakesh.blog_application.entity.Category;
import com.rakesh.blog_application.entity.Post;
import com.rakesh.blog_application.exception.ResourceNotFoundException;
import com.rakesh.blog_application.payload.PostDto;
import com.rakesh.blog_application.payload.PostResponse;
import com.rakesh.blog_application.repository.CategoryRepository;
import com.rakesh.blog_application.repository.PostRepository;
import com.rakesh.blog_application.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private CategoryRepository categoryRepository;
    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, CategoryRepository categoryRepository)
    {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }
    @Override
    public PostDto createPost(PostDto postDto) {
        Category category = categoryRepository.findById(postDto.getCategoryId())
                .orElseThrow(()->new ResourceNotFoundException("category","id", postDto.getCategoryId()));
        // convert DTO to entity
        Post post = mapToEntity(postDto);
        post.setCategory(category);

        Post newPost = postRepository.save(post);

        // convert entity to DTO
        PostDto postResponse = mapToDto(newPost);

        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir)
    {
        // create sort instance
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();

        // create pageable instance
        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> posts = postRepository.findAll(pageable);

        //get content from page
        List<Post> listOfPosts = posts.getContent();
        List<PostDto> content =  listOfPosts.stream().map(this::mapToDto).collect(Collectors.toList());

        // create postresponse instance
        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id)
    {
        return mapToDto(postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id)));
    }

    @Override
    public PostDto updatePost(PostDto postDto,Long id)
    {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        Category category = categoryRepository.findById(postDto.getCategoryId())
                        .orElseThrow(()->new ResourceNotFoundException("category","id", postDto.getCategoryId()));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        post.setCategory(category);


        Post updatedPost = postRepository.save(post);
        return mapToDto(updatedPost);
    }

    public void deletePostById(Long id)
    {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("post","id",id));
        postRepository.delete(post);
    }

    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",categoryId));
        return postRepository.findByCategoryId(categoryId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private PostDto mapToDto(Post post)
    {
          PostDto postDto = modelMapper.map(post,PostDto.class);

//        postDto.setId(post.getId());
//        postDto.setTitle(post.getTitle());
//        postDto.setDescription(post.getDescription());
//        postDto.setContent(post.getContent());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto)
    {
        Post post = modelMapper.map(postDto,Post.class);
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());

        return post;
    }
}
