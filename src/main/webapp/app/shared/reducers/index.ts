import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import animalBar, {
  AnimalBarState
} from 'app/entities/animal-bar/animal-bar.reducer';
// prettier-ignore
import wrapper, {
  WrapperState
} from 'app/entities/wrapper/wrapper.reducer';
// prettier-ignore
import puzzle, {
  PuzzleState
} from 'app/entities/puzzle/puzzle.reducer';
// prettier-ignore
import chocolate, {
  ChocolateState
} from 'app/entities/chocolate/chocolate.reducer';
// prettier-ignore
import entry, {
  EntryState
} from 'app/entities/entry/entry.reducer';
// prettier-ignore
import appUser, {
  AppUserState
} from 'app/entities/app-user/app-user.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly animalBar: AnimalBarState;
  readonly wrapper: WrapperState;
  readonly puzzle: PuzzleState;
  readonly chocolate: ChocolateState;
  readonly entry: EntryState;
  readonly appUser: AppUserState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  animalBar,
  wrapper,
  puzzle,
  chocolate,
  entry,
  appUser,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
