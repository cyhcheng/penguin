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
import lombok.AllArgsConstructor;
import org.modelmapper.TypeToken;
import org.penguin.project.tutorial.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@AllArgsConstructor
public class GraphQLConfiguration {

    private final UserService userDetailService;
    @Bean
    public GraphQL graphQL() {

        GraphQLSchema schema = new GraphQLSchemaGenerator()
                .withResolverBuilders(new AnnotatedResolverBuilder(), new PublicResolverBuilder("org.penguin.project.tutorial"))
                // https://github.com/leangen/graphql-spqr/wiki/Errors#dynamic-proxies
                .withOperationsFromSingleton(userDetailService, new TypeToken<UserService>(){}.getType()) // fix error: io.leangen.graphql.generator.exceptions.TypeMappingException: The registered object of type appears to be a dynamically generated proxy, so its type can not be reliably determined. Provide the type explicitly when registering the bean.
                .withValueMapperFactory(new JacksonValueMapperFactory()).generate();
        return GraphQL.newGraphQL(schema)
                .queryExecutionStrategy(new AsyncExecutionStrategy())
                .instrumentation(new ChainedInstrumentation(Arrays.asList(new MaxQueryComplexityInstrumentation(200), new MaxQueryDepthInstrumentation(20))))
                .build();
    }
}
