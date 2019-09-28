import { Moment } from 'moment';

export interface IEntry {
  id?: number;
  date?: Moment;
  publicId?: string;
  barId?: number;
  appUserId?: number;
}

export const defaultValue: Readonly<IEntry> = {};
