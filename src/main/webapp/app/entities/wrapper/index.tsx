import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Wrapper from './wrapper';
import WrapperDetail from './wrapper-detail';
import WrapperUpdate from './wrapper-update';
import WrapperDeleteDialog from './wrapper-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WrapperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WrapperUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WrapperDetail} />
      <ErrorBoundaryRoute path={match.url} component={Wrapper} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={WrapperDeleteDialog} />
  </>
);

export default Routes;
