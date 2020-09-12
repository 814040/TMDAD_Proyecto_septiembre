package com.mathilde.chat;

import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<Users, Long> {

}