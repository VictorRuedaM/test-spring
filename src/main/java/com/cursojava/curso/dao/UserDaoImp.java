package com.cursojava.curso.dao;

import com.cursojava.curso.models.User;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Repository
@Transactional
public class UserDaoImp implements UserDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<User> getUsers() {

        String query = "FROM User";
        List<User> result = entityManager.createQuery(query).getResultList();
        return  result;
    }


    public void deleteUser(int id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }

    @Override
    public void createUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User loginUser(User user) {
        String hashDB;

        String query = "FROM User  WHERE email = :email";
        List<User> result = entityManager.createQuery(query)
                .setParameter("email", user.getEmail())
                .getResultList();

        if(result.isEmpty()) {
            return null;
        }
        hashDB = result.get(0).getPassword();



        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if(argon2.verify(hashDB, user.getPassword())){
            return result.get(0);
        }

        return null;
    }


}
