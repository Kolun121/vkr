package ru.example.demo.helper.objects;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Breadcrumb {
    private String name;
    private String url;
    private String icon;
    
    public Breadcrumb(String name, String url, String icon, List<String> values){
    }
}
