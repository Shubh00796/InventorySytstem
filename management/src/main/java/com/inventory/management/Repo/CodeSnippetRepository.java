package com.inventory.management.Repo;

import com.inventory.management.Model.CodeSnippet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeSnippetRepository extends JpaRepository<CodeSnippet, Long> {
}