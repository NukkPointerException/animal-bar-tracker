import { WrapperPicture } from 'app/shared/model/enumerations/wrapper-picture.model';

export interface IWrapper {
  id?: number;
  picture?: WrapperPicture;
}

export const defaultValue: Readonly<IWrapper> = {};
