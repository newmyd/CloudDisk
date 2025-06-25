package user;


import database.dao.FileDao;
import database.dao.ShareDao;
import database.dao.UserDao;

public class User {
	private int userId = 0;
	private String userName = null;
	private String passWord = null;

	
	public static User registerUser(String userName, String passWord) {
		User user = new User();
		user.userName = userName;
		user.passWord = passWord;
		User res;
		try {
			res = UserDao.registerUser(user);
		} catch (Exception e) {
			return null;
		}
		return res;
	}
	
	public static User getUserById(int id) {
		if (id <= 0)
			return null;

		User user;
		try {
			user = UserDao.getUserById(id);
		} catch (Exception e) {
			return null;
		}

		if (user.getUserId() > 0)
			return user;
		
		return null;
	}
	
	public static User getUserByName(String userName) {
		if (userName == null)
			return null;

		User user;
		try {
			user = UserDao.getUserByName(userName);
		} catch (Exception e) {
			return null;
		}

		if (user.getUserId() > 0)
			return user;

		return null;
	}
	
	public User getUser() {
		return this;
	}
	
	public int getUserId() {
		return this.userId;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassWord() {
		return this.passWord;
	}
	
	public int setUserId(int userId) {
		return this.userId = userId;
	}
	
	public String setUserName(String userName) {
		return this.userName = userName;
	}
	
	public String setPassWord(String passWord) {
		return this.passWord = passWord;
	}
	
	public User modifyUserName(String userName) {
		if (userName == null || userName.equals(""))
			return null;

		if (userName.equals(this.getUserName()))
			return this;

		User user;
		try {
			user = UserDao.getUserByName(userName);
		} catch (Exception e) {
			return null;
		}

		
		if (user.getUserId() > 0)
			return null;

		this.userName = userName;

		User res;
		try {
			res = UserDao.setUserName(this);
		} catch (Exception e) {
			return null;
		}
		return res;
	}
	
	public User modifyPassWord(String passWord) {
		if (passWord == null || passWord.equals(""))
			return null;
		
		this.passWord = passWord;

		User res;
		try {
			res = UserDao.setPassWord(this);
		} catch (Exception e) {
			return null;
		}
		return res;
	}

	public static void delUser(int user) {
		try {
			UserDao.delUser(user);
			FileDao.delUser(user);
			ShareDao.delUser(user);
		} catch (Exception ignored) {
		}
	}
}
