import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AnimalBar from './animal-bar';
import AnimalBarDetail from './animal-bar-detail';
import AnimalBarUpdate from './animal-bar-update';
import AnimalBarDeleteDialog from './animal-bar-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AnimalBarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AnimalBarUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AnimalBarDetail} />
      <ErrorBoundaryRoute path={match.url} component={AnimalBar} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={AnimalBarDeleteDialog} />
  </>
);

export default Routes;
