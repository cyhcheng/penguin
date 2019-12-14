package org.penguin.project.tutorial.config;

import graphql.GraphQL;
import graphql.analysis.MaxQueryComplexityInstrumentation;
import graphql.analysis.MaxQueryDepthInstrumentation;
import graphql.execution.AsyncExecutionStrategy;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.schema.GraphQLSchema;
import io.leangen.graphql.GraphQLSchemaGenerator;
import io.leangen.graphql.metadata.strategy.query.AnnotatedResolverBuilder;
import io.leangen.graphql.metadata.strategy.query.PublicResolverBuilder;
import io.leangen.graphql.metadata.strategy.value.jackson.JacksonValueMapperFactory;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class GraphQLConfiguration {

    @Bean
    public GraphQL graphQL(UserService userService) {

        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(new AnnotatedResolverBuilder(), new PublicResolverBuilder("org.penguin.project.tutorial"))
                .withOperationsFromSingleton(userService)
                .withValueMapperFactory(new JacksonValueMapperFactory()).generate();
        return GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new AsyncExecutionStrategy())
                .instrumentation(new ChainedInstrumentation(Arrays.asList(new MaxQueryComplexityInstrumentation(200), new MaxQueryDepthInstrumentation(20))))
                .build();
    }
}
