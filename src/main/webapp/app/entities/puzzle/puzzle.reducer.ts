import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPuzzle, defaultValue } from 'app/shared/model/puzzle.model';

export const ACTION_TYPES = {
  FETCH_PUZZLE_LIST: 'puzzle/FETCH_PUZZLE_LIST',
  FETCH_PUZZLE: 'puzzle/FETCH_PUZZLE',
  CREATE_PUZZLE: 'puzzle/CREATE_PUZZLE',
  UPDATE_PUZZLE: 'puzzle/UPDATE_PUZZLE',
  DELETE_PUZZLE: 'puzzle/DELETE_PUZZLE',
  RESET: 'puzzle/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPuzzle>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PuzzleState = Readonly<typeof initialState>;

// Reducer

export default (state: PuzzleState = initialState, action): PuzzleState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PUZZLE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PUZZLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PUZZLE):
    case REQUEST(ACTION_TYPES.UPDATE_PUZZLE):
    case REQUEST(ACTION_TYPES.DELETE_PUZZLE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PUZZLE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PUZZLE):
    case FAILURE(ACTION_TYPES.CREATE_PUZZLE):
    case FAILURE(ACTION_TYPES.UPDATE_PUZZLE):
    case FAILURE(ACTION_TYPES.DELETE_PUZZLE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PUZZLE_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PUZZLE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PUZZLE):
    case SUCCESS(ACTION_TYPES.UPDATE_PUZZLE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PUZZLE):
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

const apiUrl = 'api/puzzles';

// Actions

export const getEntities: ICrudGetAllAction<IPuzzle> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PUZZLE_LIST,
  payload: axios.get<IPuzzle>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPuzzle> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PUZZLE,
    payload: axios.get<IPuzzle>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPuzzle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PUZZLE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPuzzle> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PUZZLE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPuzzle> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PUZZLE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
