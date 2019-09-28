import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AnimalBar from './animal-bar';
import Wrapper from './wrapper';
import Puzzle from './puzzle';
import Chocolate from './chocolate';
import Entry from './entry';
import AppUser from './app-user';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}/animal-bar`} component={AnimalBar} />
      <ErrorBoundaryRoute path={`${match.url}/wrapper`} component={Wrapper} />
      <ErrorBoundaryRoute path={`${match.url}/puzzle`} component={Puzzle} />
      <ErrorBoundaryRoute path={`${match.url}/chocolate`} component={Chocolate} />
      <ErrorBoundaryRoute path={`${match.url}/entry`} component={Entry} />
      <ErrorBoundaryRoute path={`${match.url}/app-user`} component={AppUser} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
