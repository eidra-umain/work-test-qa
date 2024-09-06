'use client';

import { useEffect, useState } from 'react';
import FilterBar from './components/FilterBar';
import FilterPanel from './components/FilterPanel';
import Text from './components/Text';
import RestaurantList from './components/RestaurantList';
import Logo from './components/Logo';
import { API_URL } from './api_url';
import './page.scss';
import { deliveryTimes } from './constants';

const Home = () => {
  // Filters that are updated through FilterBar and FilterPanel:
  const [categoryFilter, setCategoryFilter] = useState([]);
  const [deliveryFilter, setDeliveryFilter] = useState([]);
  const [priceFilter, setPriceFilter] = useState([]);

  // Creates a function that is used for updating a filter, e.g. categoryFilter:
  const createHandleFilterFunction = (filter, setFilter) => {
    return (id) => {
      const newFilter = filter.includes(id)
        ? filter.filter((item) => item != id)
        : [...filter, id];
      setFilter(newFilter);
      // console.log(newFilter);
    };
  };

  const handleCategoryFilter = createHandleFilterFunction(categoryFilter, setCategoryFilter);
  const handleDeliveryFilter = createHandleFilterFunction(deliveryFilter, setDeliveryFilter);
  const handlePriceFilter = createHandleFilterFunction(priceFilter, setPriceFilter);

  const [categories, setCategories] = useState([]);
  const [prices, setPrices] = useState([]);
  const [restaurants, setRestaurants] = useState([]);

  const [showMobilePage, setShowMobilePage] = useState(false);
  const [pageFullyLoaded, setPageFullyLoaded] = useState(false);

  // When page loads, make API calls to get necessary data and set
  // pageFullyLoaded to true, also determine whether the screen is mobile width
  // and therefore should show MobileStartPage first:
  useEffect(() => {
    fetch(`${API_URL}/api/filter`)
      .then((response) => response.json())
      .then((data) => setCategories(data.filters))
      .catch((error) => console.log(error));

    fetch(`${API_URL}/api/price-range`)
      .then((response) => response.json())
      .then((data) => setPrices(data))
      .catch((error) => console.log(error));

    fetch(`${API_URL}/api/restaurants`)
      .then((response) => response.json())
      .then((data) => setRestaurants(data.restaurants))
      .catch((error) => console.log(error));

    setPageFullyLoaded(true);
    setShowMobilePage(window.innerWidth < 700 ? true : false);
  }, []);

  const MobileStartPage = () => {
    return (
      <div
        className='mobile-container'
        style={showMobilePage ? {} : { display: 'none' }}
      >
        <Logo color='white' />

        <div className='mobile-message'>
          <div className='mobile-message-title'>Treat yourself.</div>
          <div className='mobile-message-text'>
            Find the best restaurants in your city and get it delivered to your
            place!
          </div>
        </div>

        <div className='continue-button-container'>
          <div
            className='continue-button'
            onClick={() => setShowMobilePage(false)}
          >
            Continue
          </div>
        </div>
      </div>
    );
  };

  return (
    <div style={pageFullyLoaded ? { display: 'block' } : { display: 'none' }}>
      {showMobilePage ? (
        <MobileStartPage />
      ) : (
        // Main page for the app:
        <main>
          <Logo color='black' />

          <FilterPanel
            categories={categories}
            handleCategoryFilter={handleCategoryFilter}
            categoryFilter={categoryFilter}
            deliveryTimes={deliveryTimes}
            handleDeliveryFilter={handleDeliveryFilter}
            deliveryFilter={deliveryFilter}
            prices={prices}
            handlePriceFilter={handlePriceFilter}
            priceFilter={priceFilter}
          />

          <div className='main-panel'>
            <FilterBar
              categories={categories}
              handleCategoryFilter={handleCategoryFilter}
              categoryFilter={categoryFilter}
            />

            <div id='restaurants-title'>
              <Text type='display'>Restaurant's</Text>
            </div>

            <RestaurantList
              restaurants={restaurants}
              categoryFilter={categoryFilter}
              deliveryFilter={deliveryFilter}
              priceFilter={priceFilter}
            />
          </div>
        </main>
      )}
    </div>
  );
};

export default Home;
