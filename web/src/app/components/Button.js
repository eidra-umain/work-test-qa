'use client';

import { useState } from 'react';
import './style/Button.scss';

const Button = ({ children, id, handleClick, buttonType, selected }) => {
  let dynamicStyle = selected
    ? { backgroundColor: 'var(--theme-button-selected)' }
    : { backgroundColor: 'var(--theme-white)' };

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
