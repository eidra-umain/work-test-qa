'use client';

import { useState, useEffect } from 'react';
import './style/RestaurantCard.scss';
import Text from './Text';
import { API_URL } from '../api_url';
import scssVariables from './style/variables.module.scss';
import RestaurantButton from './RestaurantButton';

const RestaurantCard = ({ restaurant }) => {
  const [restaurantOpen, setRestaurantOpen] = useState(false);

  // Make API request to check whether this restaurant is open:
  useEffect(() => {
    fetch(`${API_URL}/api/open/${restaurant.id}`)
      .then((response) => response.json())
      .then((data) => setRestaurantOpen(data.is_open))
      .catch((error) => console.log(error));
  }, []);

  return (
    <div className='restaurant-card'>
      {!restaurantOpen && (
        <div className='closed-overlay'>
          <div className='message'>
            <Text type='body'>Opens tomorrow at 12 pm</Text>
          </div>
        </div>
      )}

      <div className='upper-row'>
        <span>
          <span
            className='badge open-status-badge'
            style={{ marginLeft: '20px' }}
          >
            <Text type='body'>
              <span
                className='circle'
                style={
                  restaurantOpen
                    ? { backgroundColor: scssVariables.themeGreen }
                    : { backgroundColor: scssVariables.themeBlack }
                }
              ></span>
              {restaurantOpen ? <>Open</> : <>Closed</>}
            </Text>
          </span>

          {restaurantOpen && (
            <span className='badge delivery-time-badge'>
              <Text type='body'>{restaurant.delivery_time_minutes} min</Text>
            </span>
          )}
        </span>

        <span className='image'>
          <img
            src={`${API_URL}/${restaurant.image_url}`}
            className={restaurantOpen ? "" : "restaurant-closed"}
          />
        </span>
      </div>

      <div className='lower-row'>
        <span
          className='restaurant-name'
          style={
            restaurantOpen
              ? { color: scssVariables.themeBlack }
              : { color: scssVariables.themeButtonSelected }
          }
        >
          <Text type='h1' inLine={true}>
            {restaurant.name}
          </Text>
        </span>

        <RestaurantButton restaurantOpen={restaurantOpen} />
      </div>
    </div>
  );
};

export default RestaurantCard;
