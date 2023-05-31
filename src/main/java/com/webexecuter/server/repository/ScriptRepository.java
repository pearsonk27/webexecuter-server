package com.webexecuter.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webexecuter.server.entity.Script;

public interface ScriptRepository extends JpaRepository<Script, Long> {
}
