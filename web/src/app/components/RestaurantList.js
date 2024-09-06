import './style/RestaurantList.scss';
import Text from './Text';
import RestaurantCard from './RestaurantCard.js';
import { deliveryTimes } from '../constants';

// Returns true if there are any elements that exist in both lists l1 and l2:
const anyMatches = (l1, l2) => {
  return l1.filter((item) => l2.includes(item)).length > 0;
};

// Returns true if time is in the selected deliveryFilter:
const isInDeliveryFilter = (time, deliveryFilterIntervals) => {
  for (let i in deliveryFilterIntervals) {
    const intervalId = deliveryFilterIntervals[i];
    const intervalObject = deliveryTimes.find((item) => item.id === intervalId);

    const inInterval =
      intervalObject.max == '' // if there is no upper limit (1h+ option)
        ? time >= intervalObject.min
        : time >= intervalObject.min && time <= intervalObject.max;

    if (inInterval) {
      return true; // found in this intervalObject
    }
  }
  return false; // time was not found in any of the selected intervals (deliveryFilter)
};

const RestaurantList = ({
  restaurants,
  categoryFilter,
  deliveryFilter,
  priceFilter,
}) => {

  // Filter out restaurants based on selected categoryFilter:
  if (categoryFilter.length > 0) {
    restaurants = restaurants.filter((r) =>
      anyMatches(r.filter_ids, categoryFilter)
    );
  }

  // Filter out restaurants based on selected deliveryFilter:
  if (deliveryFilter.length > 0) {
    restaurants = restaurants.filter((r) =>
      isInDeliveryFilter(r.delivery_time_minutes, deliveryFilter)
    );
  }

  // Filter out restaurants based on selected priceFilter:
  if (priceFilter.length > 0) {
    restaurants = restaurants.filter((r) =>
      priceFilter.includes(r.price_range_id)
    );
  }

  return (
    <div className='restaurant-list'>
      {restaurants.map((item) => (
        <RestaurantCard key={item.id} restaurant={item} />
      ))}
    </div>
  );
};

export default RestaurantList;
