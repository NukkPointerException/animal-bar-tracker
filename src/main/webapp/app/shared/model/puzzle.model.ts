import { PuzzleType } from 'app/shared/model/enumerations/puzzle-type.model';

export interface IPuzzle {
  id?: number;
  type?: PuzzleType;
}

export const defaultValue: Readonly<IPuzzle> = {};
