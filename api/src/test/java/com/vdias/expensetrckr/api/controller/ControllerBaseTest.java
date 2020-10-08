package com.vdias.expensetrckr.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vdias.expensetrckr.configuration.JwtTokenUtil;
import com.vdias.expensetrckr.domain.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

/**
 * Base class for Controller tests.
 */
public class ControllerBaseTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected JwtTokenUtil jwtTokenUtil;

    @MockBean
    protected AuthenticationService authenticationService;

    /**
     * Executes a GET request to the given url.
     *
     * @param urlTemplate the url
     * @return the result actions
     * @throws Exception
     */
    protected ResultActions testGet(final String urlTemplate) throws Exception {
        final MockHttpServletRequestBuilder builder = get(urlTemplate);

        return mockMvc.perform(withDefaultParameters(builder));
    }

    /**
     * Executes a POST request to the given url sending the postObject argument as the request content.
     *
     * @param urlTemplate the url
     * @param postObject  request content
     * @return the result actions
     * @throws Exception
     */
    protected ResultActions testPost(final String urlTemplate, final Object postObject) throws Exception {
        final MockHttpServletRequestBuilder builder = post(urlTemplate).content(objectMapper.writeValueAsString(postObject));

        return mockMvc.perform(withDefaultParameters(builder));
    }

    /**
     * Executes a PUT request to the given url sending the putObject argument as the request content.
     *
     * @param urlTemplate the url
     * @param putObject   request content
     * @return the result actions
     * @throws Exception
     */
    protected ResultActions testPut(final String urlTemplate, final Object putObject) throws Exception {
        final MockHttpServletRequestBuilder builder = put(urlTemplate).content(objectMapper.writeValueAsString(putObject));

        return mockMvc.perform(withDefaultParameters(builder));
    }

    /**
     * Executes a DELETE request to the given url.
     *
     * @param urlTemplate the url
     * @return the result actions
     * @throws Exception
     */
    protected ResultActions testDelete(final String urlTemplate) throws Exception {
        final MockHttpServletRequestBuilder builder = delete(urlTemplate);

        return mockMvc.perform(withDefaultParameters(builder));
    }

    /**
     * Applies the default parameters to every request.
     *
     * @param builder the request builder
     * @return the request builder with default parameters applied
     */
    protected MockHttpServletRequestBuilder withDefaultParameters(final MockHttpServletRequestBuilder builder) {
        return builder.contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8");
    }


}
