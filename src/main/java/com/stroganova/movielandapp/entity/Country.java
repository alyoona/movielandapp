package com.stroganova.movielandapp.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.stroganova.movielandapp.view.View;
import lombok.Data;

@Data
@JsonView(View.Summary.class)
public class Country {
   private long id;
   private String name;
}
