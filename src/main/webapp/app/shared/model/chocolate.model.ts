import { ChocolatePicture } from 'app/shared/model/enumerations/chocolate-picture.model';

export interface IChocolate {
  id?: number;
  leftImage?: ChocolatePicture;
  rightImage?: ChocolatePicture;
}

export const defaultValue: Readonly<IChocolate> = {};
