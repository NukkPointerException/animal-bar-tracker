import { IEntry } from 'app/shared/model/entry.model';

export interface IAppUser {
  id?: number;
  userName?: string;
  firstName?: string;
  lastName?: string;
  email?: string;
  entries?: IEntry[];
}

export const defaultValue: Readonly<IAppUser> = {};
