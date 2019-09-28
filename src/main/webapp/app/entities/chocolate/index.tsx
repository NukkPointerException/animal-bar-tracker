import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Chocolate from './chocolate';
import ChocolateDetail from './chocolate-detail';
import ChocolateUpdate from './chocolate-update';
import ChocolateDeleteDialog from './chocolate-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ChocolateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ChocolateUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ChocolateDetail} />
      <ErrorBoundaryRoute path={match.url} component={Chocolate} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={ChocolateDeleteDialog} />
  </>
);

export default Routes;
