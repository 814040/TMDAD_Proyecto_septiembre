package com.mathilde.chat;

import org.springframework.data.jpa.repository.JpaRepository;

interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}