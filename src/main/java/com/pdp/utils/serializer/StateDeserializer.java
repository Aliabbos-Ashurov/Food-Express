package com.pdp.utils.serializer;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pdp.telegram.state.DefaultState;
import com.pdp.telegram.state.State;
import com.pdp.telegram.state.telegramDeliverer.ActiveOrderManagementState;
import com.pdp.telegram.state.telegramDeliverer.DeliveryMenuState;
import com.pdp.telegram.state.telegramUser.*;

import java.lang.reflect.Type;

/**
 * @author Aliabbos Ashurov
 * @since 16/May/2024  09:46
 **/
public class StateDeserializer implements JsonDeserializer<State> {
    @SuppressWarnings("unchecked")
    @Override
    public State deserialize(JsonElement json, Type typeOfT, com.google.gson.JsonDeserializationContext context) throws JsonParseException {
        String value = json.getAsString();
        String[] parts = value.split("\\.");

        if (parts.length == 1) {
            try {
                return DefaultState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return UserMenuOptionState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return OrderPlacementState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return UserViewState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return ConfirmOrderState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return MyOrderState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return CourierRegistrationState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return ConfirmationState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return DeliveryMenuState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return OrderManagementState.valueOf(parts[0]);
            } catch (IllegalArgumentException ignored) {
            }
            try {
                return ActiveOrderManagementState.valueOf(parts[0]);
            } catch (IllegalArgumentException e) {
                throw new JsonParseException("Invalid state value: " + value);
            }
        } else if (parts.length == 2) {
            try {
                Class<?> clazz = Class.forName(parts[0]);
                if (clazz.isEnum()) {
                    return (State) Enum.valueOf((Class<Enum>) clazz, parts[1]);
                }
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        throw new JsonParseException("Invalid state value: " + value);
    }
}
