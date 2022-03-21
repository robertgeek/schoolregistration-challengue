package com.example.schoolregistration.students.dto;

import com.example.schoolregistration.courses.dto.CourseDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class StudentCoursesDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String address;
    private List<CourseDto> coursesSubscribed = new ArrayList<>();
}
