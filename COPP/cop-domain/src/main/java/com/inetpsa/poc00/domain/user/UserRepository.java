package com.inetpsa.poc00.domain.user;

import java.util.List;

import org.seedstack.business.domain.GenericRepository;

/**
 * The Interface UserRepository.
 */
public interface UserRepository extends GenericRepository<User, Long> {

	/**
	 * Save user.
	 *
	 * @param user the user
	 * @return the user
	 */
	User saveUser(User user);

	/**
	 * Persist user.
	 *
	 * @param user the user
	 */
	void persistUser(User user);

	/**
	 * Delete all.
	 *
	 * @return the int
	 */
	int deleteAll();

	/**
	 * Count.
	 *
	 * @return the long
	 */
	long count();

	/**
	 * Gets the all users.
	 *
	 * @return the all users
	 */
	List<User> getAllUsers();

	/**
	 * Delete user.
	 *
	 * @param id the id
	 * @return the int
	 */
	public int deleteUser(Long id);

	/**
	 * Gets the users by team id.
	 *
	 * @param id the id
	 * @return the users by team id
	 */
	List<User> getUsersByTeamId(Long id);

	/**
	 * Gets the user by id.
	 *
	 * @param userId the user id
	 * @return the user by id
	 */
	User getUserById(String userId);
}
