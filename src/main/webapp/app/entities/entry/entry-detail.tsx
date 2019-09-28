import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './entry.reducer';
import { IEntry } from 'app/shared/model/entry.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IEntryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class EntryDetail extends React.Component<IEntryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { entryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Entry [<b>{entryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="date">Date</span>
            </dt>
            <dd>
              <TextFormat value={entryEntity.date} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="publicId">Public Id</span>
            </dt>
            <dd>{entryEntity.publicId}</dd>
            <dt>Bar</dt>
            <dd>{entryEntity.barId ? entryEntity.barId : ''}</dd>
            <dt>App User</dt>
            <dd>{entryEntity.appUserId ? entryEntity.appUserId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/entry" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/entry/${entryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ entry }: IRootState) => ({
  entryEntity: entry.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EntryDetail);
