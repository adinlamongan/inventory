package com.adn.inventory;

import com.adn.inventory.filter.LoggingFilter;
import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;


@SpringBootApplication
public class InventoryApplication {



	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingFilter.class);


	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper(){
//		ModelMapper modelMapper = new ModelMapper();
//		Condition skip = context ->  !context.getMapping().getLastDestinationProperty().getName().equals("id");
//		modelMapper.getConfiguration().setPropertyCondition(skip);
//		skip = context ->  !context.getMapping().getLastDestinationProperty().getName().equals("version");
//		modelMapper.getConfiguration().setPropertyCondition(skip);
//		skip = context ->  !context.getMapping().getLastDestinationProperty().getName().equals("createdAt");
//		modelMapper.getConfiguration().setPropertyCondition(skip);
//		skip = context ->  !context.getMapping().getLastDestinationProperty().getName().equals("createdBy");
//		modelMapper.getConfiguration().setPropertyCondition(skip);
//		skip = context ->  !context.getMapping().getLastDestinationProperty().getName().equals("updatedAt");
//		modelMapper.getConfiguration().setPropertyCondition(skip);
//		skip = context ->  !context.getMapping().getLastDestinationProperty().getName().equals("updatedBy");
//		modelMapper.getConfiguration().setPropertyCondition(skip);
		return new ModelMapper();
	}





}
