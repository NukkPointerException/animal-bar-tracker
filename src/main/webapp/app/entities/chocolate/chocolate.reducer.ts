import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IChocolate, defaultValue } from 'app/shared/model/chocolate.model';

export const ACTION_TYPES = {
  FETCH_CHOCOLATE_LIST: 'chocolate/FETCH_CHOCOLATE_LIST',
  FETCH_CHOCOLATE: 'chocolate/FETCH_CHOCOLATE',
  CREATE_CHOCOLATE: 'chocolate/CREATE_CHOCOLATE',
  UPDATE_CHOCOLATE: 'chocolate/UPDATE_CHOCOLATE',
  DELETE_CHOCOLATE: 'chocolate/DELETE_CHOCOLATE',
  RESET: 'chocolate/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IChocolate>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type ChocolateState = Readonly<typeof initialState>;

// Reducer

export default (state: ChocolateState = initialState, action): ChocolateState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CHOCOLATE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CHOCOLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_CHOCOLATE):
    case REQUEST(ACTION_TYPES.UPDATE_CHOCOLATE):
    case REQUEST(ACTION_TYPES.DELETE_CHOCOLATE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_CHOCOLATE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CHOCOLATE):
    case FAILURE(ACTION_TYPES.CREATE_CHOCOLATE):
    case FAILURE(ACTION_TYPES.UPDATE_CHOCOLATE):
    case FAILURE(ACTION_TYPES.DELETE_CHOCOLATE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHOCOLATE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_CHOCOLATE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_CHOCOLATE):
    case SUCCESS(ACTION_TYPES.UPDATE_CHOCOLATE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_CHOCOLATE):
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

const apiUrl = 'api/chocolates';

// Actions

export const getEntities: ICrudGetAllAction<IChocolate> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_CHOCOLATE_LIST,
  payload: axios.get<IChocolate>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IChocolate> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CHOCOLATE,
    payload: axios.get<IChocolate>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IChocolate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CHOCOLATE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IChocolate> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CHOCOLATE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IChocolate> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CHOCOLATE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
