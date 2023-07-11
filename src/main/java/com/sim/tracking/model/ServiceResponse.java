package com.sim.tracking.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.ToString;
import org.reflections.ReflectionUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class ServiceResponse<T extends IResponse> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6071955880800942089L;
	private ResponseStatus status = new ResponseStatus(200, "SUCCESS");
	private T response;
	private ViewResponse viewResponse;

	T getInstanceOfT(boolean shouldUpdateFields) {
		ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<T> type = (Class<T>) superClass.getActualTypeArguments()[0];
		try {
			T t = type.newInstance();
			if (shouldUpdateFields) {
				updateDefaultValueForMethod(t);
			}
			return t;
		} catch (Exception e) {
			// Oops, no default constructor
			throw new RuntimeException(e);
		}
	}

	Object getInstance(Class type, boolean shouldUpdateFields) {
		if (type == null) {
			return null;
		}
		try {
			if (Boolean.class.isAssignableFrom(type) || type.isAssignableFrom(Boolean.class)) {
				return new Boolean(true);
			}

			if (Number.class.isAssignableFrom(type) || type.isAssignableFrom(Number.class)) {
				Object t = type.getDeclaredConstructor(String.class).newInstance("0");
				return t;
			}

			Object t = type.newInstance();
			if (shouldUpdateFields) {
				updateDefaultValueForMethod(t);
			}
			return t;
		} catch (Exception e) {
			// Oops, no default constructor
			throw new RuntimeException(e);
		}
	}

	/**
	 * @param type
	 * @param t
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void updateDefaultValueForMethod(Object t)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Set<Method> getters = ReflectionUtils.getAllMethods(t.getClass(),
				ReflectionUtils.withModifier(Modifier.PUBLIC), ReflectionUtils.withPrefix("get"));
		if (CollectionUtils.isEmpty(getters)) {
			return;
		}
		for (Method getter : getters) {
			try {
				if (List.class.isAssignableFrom(getter.getReturnType())
						|| getter.getReturnType().isAssignableFrom(List.class)) {
					List object = (List) getter.invoke(t);
					if (object == null) {
						String str = getter.getName();
						String setter = "set" + str.substring(3, str.length());
						Method tempMethod = t.getClass().getMethod(setter, List.class);
						tempMethod.invoke(t, new ArrayList<>());
					}
				} else {
					Object object = getter.invoke(t);
					if (object == null) {
						String str = getter.getName();

						String setter = "set" + str.substring(3, str.length());
						t.getClass().getMethod(setter, getter.getReturnType()).invoke(t,
								getInstance(getter.getReturnType(), false));
					}
				}
			} catch (Exception exception) {
				// exception.printStackTrace();
			}
		}
	}

	public ServiceResponse() {

	}

	public ServiceResponse(T response) {
		this.response = response;
	}

	public ServiceResponse(ResponseStatus status) {
		this.status = status;
	}

	public ServiceResponse(String successMessage) {
		this.status = new ResponseStatus(200, successMessage);
	}

	public ResponseStatus getStatus() {
		return status;
	}

	public void setStatus(ResponseStatus status) {
		this.status = status;
	}

	public T getResponse() {
		if (response == null) {
			try {
				return getInstanceOfT(true);
			} catch (Exception exception) {
				return response;
			}
		}
		try {
			updateDefaultValueForMethod(response);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}

	/**
	 * @return the viewResponse
	 */
	public ViewResponse getViewResponse() {
		return viewResponse;
	}

	/**
	 * @param viewResponse the viewResponse to set
	 */
	public void setViewResponse(ViewResponse viewResponse) {
		this.viewResponse = viewResponse;
	}
}
