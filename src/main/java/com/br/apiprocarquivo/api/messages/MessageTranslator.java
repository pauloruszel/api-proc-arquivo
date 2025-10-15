package com.br.apiprocarquivo.api.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageTranslator {

    private final MessageSource messageSource;

    public ApiMessageResponse toResponse(final ApiMessageCode code, final Object... parameters) {
        final var message = messageSource.getMessage(code.getMessageKey(), parameters, LocaleContextHolder.getLocale());
        return new ApiMessageResponse(code.getCode(), message);
    }
}
