package org.ry0mry.uploadtokindle.controller.user;

import org.ry0mry.uploadtokindle.dataaccess.UploadUserDao;
import org.ry0mry.uploadtokindle.model.UploadUser;
import org.slim3.controller.Controller;
import org.slim3.controller.Navigation;
import org.slim3.util.BeanUtil;
import org.slim3.util.CopyOptions;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ConfigureController extends Controller {

	private final UserService userService = UserServiceFactory.getUserService();

	// TODO: make mockable
	private final UploadUserDao userDao = new UploadUserDao();

	@Override
    public Navigation run() throws Exception {
		User user = this.userService.getCurrentUser();
		UploadUser uploadUser = this.userDao.getUploadUser(user);
		if(uploadUser==null) {
    		uploadUser = new UploadUser();
    		uploadUser.setUser(user);
		}

		if(this.isGet()) {
    		requestScope("uploadUser", uploadUser);
            return forward("configure.jsp");
    	} else {
    		BeanUtil.copy(this.request, uploadUser, new CopyOptions().include("uploadDestAddress","addrType"));
    		this.userDao.updateUploadUser(uploadUser);
    		
    		return redirect("/");
    	}
    }
}
