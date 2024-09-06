import { API_URL } from '../api_url';
import Button from './Button';
import './style/FilterBar.scss';
import Text from './Text';

const FilterBar = ({ categories, handleCategoryFilter, categoryFilter }) => {
  return (
    <div className='filter-bar'>
      {categories.map((item) => (
        <Button
          buttonType='filter-card'
          key={item.id}
          id={item.id}
          handleClick={handleCategoryFilter}
          selected={categoryFilter.includes(item.id)}
        >
          <span className='name'>
            <Text type='title'>{item.name}</Text>
          </span>

          <span className='image'>
            <img
              src={`${API_URL}/${item.image_url}`}
            />
          </span>
        </Button>
      ))}
    </div>
  );
};

export default FilterBar;
