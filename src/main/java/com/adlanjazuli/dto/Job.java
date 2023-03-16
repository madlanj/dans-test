package com.adlanjazuli.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Job {
	private String id;
	private String type;
	private String url;
	private String created_at;
	private String company;
	private String company_url;
	private String location;
	private String title;
	private String description;
	private String how_to_apply;
	private String company_logo;
}
