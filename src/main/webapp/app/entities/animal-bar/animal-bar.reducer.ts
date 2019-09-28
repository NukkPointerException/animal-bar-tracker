import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAnimalBar, defaultValue } from 'app/shared/model/animal-bar.model';

export const ACTION_TYPES = {
  FETCH_ANIMALBAR_LIST: 'animalBar/FETCH_ANIMALBAR_LIST',
  FETCH_ANIMALBAR: 'animalBar/FETCH_ANIMALBAR',
  CREATE_ANIMALBAR: 'animalBar/CREATE_ANIMALBAR',
  UPDATE_ANIMALBAR: 'animalBar/UPDATE_ANIMALBAR',
  DELETE_ANIMALBAR: 'animalBar/DELETE_ANIMALBAR',
  RESET: 'animalBar/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAnimalBar>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type AnimalBarState = Readonly<typeof initialState>;

// Reducer

export default (state: AnimalBarState = initialState, action): AnimalBarState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_ANIMALBAR_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ANIMALBAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ANIMALBAR):
    case REQUEST(ACTION_TYPES.UPDATE_ANIMALBAR):
    case REQUEST(ACTION_TYPES.DELETE_ANIMALBAR):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_ANIMALBAR_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ANIMALBAR):
    case FAILURE(ACTION_TYPES.CREATE_ANIMALBAR):
    case FAILURE(ACTION_TYPES.UPDATE_ANIMALBAR):
    case FAILURE(ACTION_TYPES.DELETE_ANIMALBAR):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANIMALBAR_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_ANIMALBAR):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ANIMALBAR):
    case SUCCESS(ACTION_TYPES.UPDATE_ANIMALBAR):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ANIMALBAR):
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

const apiUrl = 'api/animal-bars';

// Actions

export const getEntities: ICrudGetAllAction<IAnimalBar> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_ANIMALBAR_LIST,
  payload: axios.get<IAnimalBar>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IAnimalBar> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ANIMALBAR,
    payload: axios.get<IAnimalBar>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAnimalBar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ANIMALBAR,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAnimalBar> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ANIMALBAR,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAnimalBar> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ANIMALBAR,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
