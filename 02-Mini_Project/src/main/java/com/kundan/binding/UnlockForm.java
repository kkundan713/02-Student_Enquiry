package com.kundan.binding;

import lombok.Data;

@Data
public class UnlockForm {
	private String email;
	private String tempPwd;
	private String newPwd;
	private String confPwd;

}
