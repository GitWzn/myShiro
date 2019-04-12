package com.wzn.bean;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private int uid;
	private String userName;
	private String userPassword;
	private String describe;
}
