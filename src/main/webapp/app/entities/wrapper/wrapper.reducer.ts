import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IWrapper, defaultValue } from 'app/shared/model/wrapper.model';

export const ACTION_TYPES = {
  FETCH_WRAPPER_LIST: 'wrapper/FETCH_WRAPPER_LIST',
  FETCH_WRAPPER: 'wrapper/FETCH_WRAPPER',
  CREATE_WRAPPER: 'wrapper/CREATE_WRAPPER',
  UPDATE_WRAPPER: 'wrapper/UPDATE_WRAPPER',
  DELETE_WRAPPER: 'wrapper/DELETE_WRAPPER',
  RESET: 'wrapper/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IWrapper>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type WrapperState = Readonly<typeof initialState>;

// Reducer

export default (state: WrapperState = initialState, action): WrapperState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_WRAPPER_LIST):
    case REQUEST(ACTION_TYPES.FETCH_WRAPPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_WRAPPER):
    case REQUEST(ACTION_TYPES.UPDATE_WRAPPER):
    case REQUEST(ACTION_TYPES.DELETE_WRAPPER):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_WRAPPER_LIST):
    case FAILURE(ACTION_TYPES.FETCH_WRAPPER):
    case FAILURE(ACTION_TYPES.CREATE_WRAPPER):
    case FAILURE(ACTION_TYPES.UPDATE_WRAPPER):
    case FAILURE(ACTION_TYPES.DELETE_WRAPPER):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_WRAPPER_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_WRAPPER):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_WRAPPER):
    case SUCCESS(ACTION_TYPES.UPDATE_WRAPPER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_WRAPPER):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/wrappers';

// Actions

export const getEntities: ICrudGetAllAction<IWrapper> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_WRAPPER_LIST,
  payload: axios.get<IWrapper>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IWrapper> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_WRAPPER,
    payload: axios.get<IWrapper>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IWrapper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_WRAPPER,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IWrapper> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_WRAPPER,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IWrapper> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_WRAPPER,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
