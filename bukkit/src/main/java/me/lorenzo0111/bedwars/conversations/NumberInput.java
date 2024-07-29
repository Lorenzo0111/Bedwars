package me.lorenzo0111.bedwars.conversations;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class NumberInput extends NumericPrompt {
    private final String prompt;
    private final Consumer<Integer> consumer;

    public NumberInput(String prompt, Consumer<Integer> consumer) {
        this.prompt = prompt;
        this.consumer = consumer;
    }

    @Nullable
    @Override
    protected Prompt acceptValidatedInput(@NotNull ConversationContext context, @NotNull Number number) {
        consumer.accept(number.intValue());
        return END_OF_CONVERSATION;
    }

    @NotNull
    @Override
    public String getPromptText(@NotNull ConversationContext conversationContext) {
        return prompt;
    }

    @Override
    protected boolean isNumberValid(@NotNull ConversationContext context, @NotNull Number input) {
        return input.intValue() > 0;
    }

    @Nullable
    @Override
    protected String getFailedValidationText(@NotNull ConversationContext context, @NotNull Number invalidInput) {
        return BedwarsPlugin.getInstance().getPrefixed("invalid-number");
    }
}