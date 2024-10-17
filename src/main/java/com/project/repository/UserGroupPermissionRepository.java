package com.project.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.project.table.UserGroupPermission;

public interface UserGroupPermissionRepository extends JpaRepository<UserGroupPermission, Long> {

	@Transactional
	void deleteByGroupId(Long id);

	List<UserGroupPermission> findByGroupId(Long id);

}
