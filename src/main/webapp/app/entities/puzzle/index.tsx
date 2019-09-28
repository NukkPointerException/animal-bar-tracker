import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Puzzle from './puzzle';
import PuzzleDetail from './puzzle-detail';
import PuzzleUpdate from './puzzle-update';
import PuzzleDeleteDialog from './puzzle-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PuzzleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PuzzleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PuzzleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Puzzle} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PuzzleDeleteDialog} />
  </>
);

export default Routes;
