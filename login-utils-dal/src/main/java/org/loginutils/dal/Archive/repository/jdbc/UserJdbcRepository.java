//package org.loginutils.dal.Archive.repository.jdbc;
//
//import org.loginutils.dal.Archive.repository.entity.UserEntity;
//import org.springframework.context.annotation.Profile;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//@Repository
//@Profile("jdbc")
//public class UserJdbcRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public UserJdbcRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//
//    public UserEntity findById(String userId) {
//        return jdbcTemplate.queryForObject("SELECT * FROM user WHERE id = ?", new BeanPropertyRowMapper<>(UserEntity.class), userId);
//    }
//}
