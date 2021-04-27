package com.theismann.beltExam.services;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.theismann.beltExam.models.Idea;
import com.theismann.beltExam.models.User;
import com.theismann.beltExam.repositories.IdeaRepository;
import com.theismann.beltExam.repositories.UserRepository;

@Service
public class AppService {
	
	private final UserRepository userRepository;
	private final IdeaRepository ideaRepository;
	//private final IdeaLikeRepository ideaLikeRepository
    
    // dependency injection
    public AppService(UserRepository userRepository, IdeaRepository ideaRepository) {
        this.userRepository = userRepository;
        this.ideaRepository = ideaRepository;
        //this.ideaLikeRepository = ideaLikeRepository;
    }
    
    
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    //************************
    // Find
    //************************
    
    public List<User> findAllUsers(){
    	return (List<User>)this.userRepository.findAll();
    }
    
    public User findUserById(Long id) {
    	
    	return userRepository.findById(id).orElse(null);
    }
    
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public List<Idea> findAll() {
    	return ideaRepository.findAll();
    }
    
    public Idea findIdeaById(Long id) {
    	return ideaRepository.findById(id).orElse(null);
    }
    
  //************************
    // create, update, and delete
    //************************
    
    public Idea createIdea(Idea idea) {
    	return ideaRepository.save(idea);
    }
    
    public Idea updateIdea(Idea idea) {
    	return this.ideaRepository.save(idea);
    }
   
    public void deleteIdea(Idea idea) {
    	this.ideaRepository.delete(idea);
    }
//    public IdeaLike createRelationship(IdeaLike ideaLike) {
//    	return this.ideaLikeRepository.save(ideaLike);
//    }
    
    public void updateUser(User user) {
    	this.userRepository.save(user);
    }
    
 }
	

