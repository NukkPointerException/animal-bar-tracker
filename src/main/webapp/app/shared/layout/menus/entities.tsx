import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/animal-bar">
      Animal Bar
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/wrapper">
      Wrapper
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/puzzle">
      Puzzle
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/chocolate">
      Chocolate
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/entry">
      Entry
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/app-user">
      App User
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
