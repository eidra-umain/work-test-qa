'use client';

import { useState } from 'react';

import './style/FilterPanel.scss';
import Button from './Button';
import Text from './Text';

const FilterPanel = ({
  categories,
  handleCategoryFilter,
  categoryFilter,
  deliveryTimes,
  handleDeliveryFilter,
  deliveryFilter,
  prices,
  handlePriceFilter,
  priceFilter,
}) => {
  return (
    <div className='filter-panel'>
      <span id='filter-panel-title'>
        <Text type='h1'>
          Filter
        </Text>
      </span>
      

      <div id='filter-panel-food-categories'>
        <Text type='subtitle'>FOOD CATEGORY</Text>

        {categories.map((item) => (
          <div className='new-line' key={item.id}>
            <Button
              buttonType='filter-button'
              id={item.id}
              handleClick={handleCategoryFilter}
              selected={categoryFilter.includes(item.id)}
            >
              <Text type='body'>{item.name}</Text>
            </Button>
          </div>
        ))}
      </div>

      <Text type='subtitle'>DELIVERY TIME</Text>

      <div className="delivery-time-selector-container">
        {deliveryTimes.map((item) => (
          <Button
            buttonType='filter-button delivery-time-filter-button'
            key={item.id}
            id={item.id}
            handleClick={handleDeliveryFilter}
            selected={deliveryFilter.includes(item.id)}
          >
            <Text type='body'>{item.text}</Text>
          </Button>
        ))}
      </div>

      <Text type='subtitle'>PRICE RANGE</Text>

      {prices.map((item) => (
        <Button
          buttonType='filter-button'
          key={item.id}
          id={item.id}
          handleClick={handlePriceFilter}
          selected={priceFilter.includes(item.id)}
        >
          <Text type='body'>{item.range}</Text>
        </Button>
      ))}
    </div>
  );
};

export default FilterPanel;
