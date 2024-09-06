import './style/Text.scss';

const Text = ({ children, type, inLine }) => {
  return (
    <div
      className={`${type}-text`}
      style={inLine ? { display: 'inline-block' } : {}}
    >
      {children}
    </div>
  );
};

export default Text;
