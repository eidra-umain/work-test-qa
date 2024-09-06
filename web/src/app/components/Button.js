'use client';

import { useState } from 'react';
import './style/Button.scss';
import scssVariables from "./style/variables.module.scss"

const Button = ({ children, id, handleClick, buttonType, selected }) => {
  let dynamicStyle = selected
    ? { backgroundColor: scssVariables.themeButtonSelected }
    : { backgroundColor: scssVariables.themeWhite };

  return (
    <div
      className={buttonType}
      style={dynamicStyle}
      onClick={() => {
        handleClick(id);
      }}
    >
      {children}
    </div>
  );
};

export default Button;
