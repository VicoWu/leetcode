package Q7_07_Chat_Server;

public class UserStatus {
	 private String message;//状态提示消息
	 private UserStatusType type; //当前状态
	 public UserStatus(UserStatusType type, String message) {
		 this.type = type;
		 this.message = message;
	 }
	 
	 public UserStatusType getStatusType() {
		 return type;
	 }
	 
	 public String getMessage() {
		 return message;
	 }
}
