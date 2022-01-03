package com.redthreadcomm.puppies;

import java.lang.reflect.Field;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private static final org.slf4j.Logger LOGGER =
            org.slf4j.LoggerFactory.getLogger(UserController.class);

//Creating a new User
    @PostMapping("/createUser")
    public String createNewUser(@RequestBody User user){
    String s = null;
        User userCreated = userRepository.save(user);
        if(userCreated!=null){
            s= "User created successfully";
        }
        else{
            s="User creation unsuccessful";
        }
        LOGGER.info(s);
        return s;
    }

//Authenticating User

    @PostMapping("/authUser")
    public String
    authenticateUser(@RequestBody User user){
        AtomicReference<String> s = new AtomicReference<>("User does'nt exists");
        Optional<User> authUser = userRepository.findById(user.getUserId());
        authUser.ifPresent(aUser -> {
            if ((aUser.getName()).equals(user.getName())&&(aUser.getPassword()).equals(user.getPassword())) {
                s.set("User is successfully authenticated.");
            }
            else{
                s.set("User authentication failed.");
            }
        });
        return s.get();
    }
// Get the user details.
@GetMapping("/fetchUserDetails/{userId}")
public Optional<User> getUserDetails(@PathVariable String userId) {
        Optional<User> user;
    user = userRepository.findById(userId);
    return user;
}
    // Method to update the user /post details.

    @PatchMapping("/user/{userId}")
    public void partialUpdateUser(@PathVariable final String userId, @RequestBody final User user) throws Exception{
        for (final Field field: User.class.getDeclaredFields()){
            final String fieldName = field.getName();
            if(fieldName.equals("userId")){
                continue;
            }

            final Method getter = User.class.getDeclaredMethod("get"+ StringUtils.capitalize(fieldName));
            final Object fieldValue = getter.invoke(user);

            if(Objects.nonNull(fieldValue)){
                userRepository.partialUserUpdate(userId,fieldName,fieldValue);
            }
        }
    }

    // Get all the details of an individual post.
    @GetMapping("/fetchPost/{postId}")
    public List<Post> getPostDetails(@PathVariable String postId){
        User user;
        user = userRepository.findByPostId(postId);
        return user.getPosts();
    }

    // Creating a new post by existing user.

    @PatchMapping("/createPost/{userId}")
    public void createNewPost(@PathVariable String userId, @RequestBody Post post){
        //List<Post> posts = new ArrayList<Post>();
        if(Objects.nonNull(post)){
            Optional<User> authUser = userRepository.findById(userId);
            AtomicReference<List<Post>> posts = new AtomicReference<List<Post>>();
            authUser.ifPresent(aUser -> {
                aUser.getPosts().add(post);
                posts.set(aUser.getPosts());
            });

            userRepository.partialUserUpdate(userId,"posts",posts.get());
        }
    }

// Get all the posts posted by an existing user.

    @GetMapping("/fetchPosts/{userId}")
    public List<Post> getAllPosts(@PathVariable String userId){
        List<Post> postList = new ArrayList<>();
        AtomicReference<List<Post>> posts = new AtomicReference<>();
        Optional<User> authUser = userRepository.findById(userId);
        //List<Post> posts;
        authUser.ifPresent(aUser -> {
            posts.set(aUser.getPosts());
        });
        Collections.sort(posts.get());
        postList = posts.get();
        return  postList;
    }

    //Like a Post
@PostMapping("/likePost/{postId}")
public void likePost(@RequestBody User user, @PathVariable String postId) throws Exception {
    User userDB;
    User likedUser = new User();
    likedUser.setUserId(user.getUserId());
    userDB = userRepository.findByPostId(postId);
    List<Post> posts = getAllPosts(userDB.getUserId());
    Post updatedPost = new Post();
    Post removedPost = new Post();
    for (Post post : posts) {
        if(post.getPostId().equals(postId)){
            removedPost=post;
            post.getLikedUsers().add(likedUser);
            updatedPost = post;
        }
    }
    posts.remove(removedPost);
    posts.add(updatedPost);
    userDB.setPosts(posts);
    partialUpdateUser(userDB.getUserId(),userDB);
}
    //Fetch User liked posts.

    @GetMapping("/fetchUserLikedPosts/{userId}")
    public List<Post> fetchUserLikedPosts(@PathVariable String userId){
        List<Post> posts ;
        posts = userRepository.findByUserId(userId);
        return posts;
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleUserNotFound(Exception exception) {
        LOGGER.info("An Exception occured in controller " + exception.getMessage());
    }
}
