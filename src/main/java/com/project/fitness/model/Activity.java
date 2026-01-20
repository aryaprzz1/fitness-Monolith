package com.project.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id ;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false,foreignKey= @ForeignKey(name = "fk_Activity_user"))
    @JsonIgnore // api diff user /act if same api then no JSON ignore
    private User user ;

    //enumtype --> stoeres indes 0123 in db but .String adds the name of the indexed in db
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    // TO STORE ADDITONAL DATA IN DB INFORMAT OF JSON
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "json")
    private Map<String,Object> additionalMetrics ;

    private Integer duration;
    private Integer caloriesBurned ;
    private LocalDateTime startTime ;

    @CreationTimestamp
    private LocalDateTime createdAt ;

    @UpdateTimestamp
    private LocalDateTime  updatedAt ;

    @OneToMany(mappedBy = "activity",cascade = CascadeType.ALL,orphanRemoval = true )
    @JsonIgnore
    private List<Recommendation> recommendations = new ArrayList<>() ;
}
