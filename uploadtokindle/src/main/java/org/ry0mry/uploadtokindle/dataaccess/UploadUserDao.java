package org.ry0mry.uploadtokindle.dataaccess;

import java.io.IOException;
import java.util.Properties;

import org.ry0mry.uploadtokindle.controller.UploadController;
import org.ry0mry.uploadtokindle.model.UploadUser;

import com.google.appengine.api.users.User;

public class UploadUserDao {
	public UploadUser getUploadUser(User user) {
		//return Datastore.get(UploadUser.class, KeyFactory.createKey("UploadUser", user.getUserId()));

		try {
			UploadUser uploadUser = new UploadUser();
			Properties props = new Properties();
			props.load(UploadController.class.getResourceAsStream("/secret.properties"));
			
			String sender = props.getProperty("mail.sender");
			String recipient = props.getProperty("mail.recipient");
			
			if(!user.getEmail().equals(sender)) {
				return null;
			}
			String[] split = recipient.split("@");
			uploadUser.setUploadDestAddress(split[0]);
			uploadUser.setAddrType(UploadUser.KindleAddrType.KINDOLECOM);
			uploadUser.setUser(user);
			
			return uploadUser;
		} catch (IOException e) {
			return null;
		}
	}
	
	public void updateUploadUser(UploadUser user) {
		// TODO: do nothgin for mean time
		//Datastore.put(user);
	}
}
